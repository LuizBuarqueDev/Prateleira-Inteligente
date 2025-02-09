<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { auth } from '@/assets/js/firebase';

import BaseLayout from '@/components/BaseLayout.vue';
import DAOService from '@/services/DAOService';
import Comments from '@/components/Comments.vue';

// DAOs
const bookService = new DAOService('books');
const userBookService = new DAOService('user_books');

const route = useRoute();

const defaultImage = "/img/bookImg.png";

const idBook = ref('');
const bookData = ref({});
const isBookInShelf = ref(false); // Indica se o livro está na prateleira
const userRating = ref(0); // Nota do livro
const averageRating = ref(0);

const fetchBookData = async (bookId) => {
  bookData.value = await bookService.get(bookId);
};

const checkBookInShelf = async () => {
  if (!auth.currentUser) return;

  // Busca apenas os IDs dos livros do usuário
  const userBookIds = (await userBookService.search('uid', auth.currentUser.uid)).map(entry => entry.book);
  isBookInShelf.value = userBookIds.includes(route.params.id);

  // Se o livro estiver na prateleira, carrega a nota do livro
  if (isBookInShelf.value) {
    const userBookEntry = await userBookService.search('uid', auth.currentUser.uid, 'book', route.params.id);
    userRating.value = userBookEntry[0]?.rating || 0; // Caso exista uma avaliação, usa ela
  }
};

const calculateAverageRating = async () => {
  const userBooksEntries = await userBookService.search('book', route.params.id);
  const ratings = userBooksEntries.map(entry => entry.rating).filter(rating => rating > 0);

  if (ratings.length > 0) {
    const sum = ratings.reduce((acc, rating) => acc + rating, 0);
    averageRating.value = (sum / ratings.length).toFixed(2);
  } else {
    averageRating.value = 0;
  }
};

const addBookToShelf = async () => {
  if (!auth.currentUser) {
    alert("É preciso estar logado para adicionar os livros a estante")
    return
  } ;

  await userBookService.insert({
    uid: auth.currentUser.uid,
    rating: userRating.value,
    book: route.params.id
  });

  isBookInShelf.value = true;
  calculateAverageRating();
  alert("Livro adicionado a estante")
};

const removeBookFromShelf = async () => {
  if (!auth.currentUser)  return;
  const userBooksEntries = await userBookService.search('uid', auth.currentUser.uid);
  const bookEntry = userBooksEntries.find(entry => entry.book === route.params.id);

  if (bookEntry) {
    await userBookService.delete(bookEntry.id);
    isBookInShelf.value = false;
    calculateAverageRating();
  }
};

const updateRating = async () => {
  if (auth.currentUser && isBookInShelf.value) {
    const userBooksEntries = await userBookService.search('uid', auth.currentUser.uid);
    const bookEntry = userBooksEntries.find(entry => entry.book === route.params.id);

    if (bookEntry) {
      await userBookService.update(bookEntry.id, { rating: userRating.value });
      calculateAverageRating();
    }
  }
};

onMounted(async () => {
  idBook.value = route.params.id;
  await Promise.all([fetchBookData(idBook.value), checkBookInShelf(), calculateAverageRating()]);
});
</script>

<template>
  <BaseLayout>

    <section class="row book-detail">
      <aside class="col-md-4">
        <img :src="bookData.image_link || defaultImage" alt="Capa do livro" class="book-image" />

        <div class="mt-4 ">
          <div v-if="isBookInShelf">
            <select v-model="userRating" @change="updateRating">
              <option v-for="rating in [0, 1, 2, 3, 4, 5]" :key="rating" :value="rating">{{ rating }}</option>
            </select>

            <!-- Renderiza as estrelas preenchidas -->
            <i v-for="n in userRating" :key="'filled-' + n" class="fa-solid fa-star" style="color: #FFD43B;"></i>

            <!-- Renderiza as estrelas vazias -->
            <i v-for="n in 5 - userRating" :key="'empty-' + n" class="fa-regular fa-star" style="color: #FFD43B;"></i>
          </div>
          <button @click="isBookInShelf ? removeBookFromShelf() : addBookToShelf()" type="button"
            class="btn custom-btn">
            {{ isBookInShelf ? 'Remover da Prateleira' : 'Adicionar à Prateleira' }}
          </button>
        </div>
      
      </aside>

      <div class="col-md-8">
        <h2>{{ bookData.title || 'Título não disponível' }}</h2>
        <p><strong>Autor(es):</strong> {{ bookData.authors?.join(', ') || 'Não informado' }}</p>
        <p><strong>Categoria(s):</strong> {{ bookData.categories?.join(', ') || 'Não informado' }}</p>
        <p><strong>Descrição:</strong> {{ bookData.description || 'Descrição indisponível' }}</p>
        <p><strong>Páginas:</strong> {{ bookData.page_count || 'Não informado' }}</p>
        <p><strong>Data de Publicação:</strong> {{ bookData.published_date || 'Não informado' }}</p>
        <p><strong>Editora:</strong> {{ bookData.publisher || 'Não informado' }}</p>
        <p><strong>Idioma:</strong> {{ bookData.language || 'Não informado' }}</p>
        <p><strong>Média das Notas:</strong> {{ averageRating }}</p>
      </div>

    </section>

    <Comments :bookId="idBook"/>
    
  </BaseLayout>
</template>

<style scoped>
.book-detail {
  margin: 50px;
}

.custom-btn {
  margin: 10px;
  background-color: var(--color_1);
  color: var(--color_white);
}

.book-image {
  height: 500px;
  width: 350px;
  border-radius: 8px;
  border: 2px solid red;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
</style>