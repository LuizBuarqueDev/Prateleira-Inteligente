<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { auth } from '@/assets/js/firebase';

import BaseLayout from '@/components/BaseLayout.vue';
import DAOService from '@/services/DAOService';

// DAOs
const bookService = new DAOService('books');
const userBookService = new DAOService('user_books');

const route = useRoute();

const defaultImage = "/img/bookImg.png";

const bookData = ref({});
const isBookInShelf = ref(false); // Indica se o livro está na prateleira

const fetchBookData = async (bookId) => {
    bookData.value = await bookService.get(bookId);
};

const checkBookInShelf = async () => {
  if (!auth.currentUser) return;

    // Busca apenas os IDs dos livros do usuário
    const userBookIds = (await userBookService.search('uid', auth.currentUser.uid)).map(entry => entry.book);
    isBookInShelf.value = userBookIds.includes(route.params.id);
};

const addBookToShelf = async () => {
  if (!auth.currentUser) return;

  try {
    await userBookService.insert({
      uid: auth.currentUser.uid,
      book: route.params.id
    });
    isBookInShelf.value = true;
  } catch (error) {
    console.error("Erro ao adicionar o livro à prateleira:", error);
  }
};

const removeBookFromShelf = async () => {
  if (!auth.currentUser) return;
    const userBooksEntries = await userBookService.search('uid', auth.currentUser.uid);
    const bookEntry = userBooksEntries.find(entry => entry.book === route.params.id);

    if (bookEntry) {
      await userBookService.delete(bookEntry.id);
      isBookInShelf.value = false;
    }
};

onMounted(async () => {
  const bookId = route.params.id;
  await Promise.all([fetchBookData(bookId), checkBookInShelf()]);
});
</script>

<template>
  <BaseLayout>
    <section class="row book-detail">
      <aside class="col-md-4">
        <img :src="bookData.image_link || defaultImage" alt="Capa do livro" class="book-image" />
        <div class="mt-4">
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
      </div>
    </section>
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