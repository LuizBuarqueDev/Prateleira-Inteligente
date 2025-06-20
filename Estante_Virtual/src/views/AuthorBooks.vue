<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';  

import LivroService from '@/services/LivroService';
import AutorService from '@/services/AutorService';
import BaseLayout from '@/components/BaseLayout.vue';
import BooksCards from '@/components/BooksCards.vue';
import Spinner from '@/components/Spinner.vue';

const route = useRoute();
const authorId = ref(route.params.authorId);
const isLoading = ref(false);
const books = ref([]);
const author = ref(null);

const fetchByAuthors = async (booksIds) => {

    isLoading.value = true;
    console.log('IDs dos livros do autor:', booksIds);
    const response = await LivroService.findAllByIds(booksIds);
    books.value = response.data;
    isLoading.value = false;

};

const fetchAuthor = async (authorId) => {
    isLoading.value = true;
    const response = await AutorService.findById(authorId);
    author.value = response.data;
    console.log('Autor carregado:', author.value);
    
    if (author.value?.idLivros?.length) {
      await fetchByAuthors(author.value.idLivros);
    } else {
      books.value = [];
    }
    isLoading.value = false;
};

onMounted(() => {
  fetchAuthor(authorId.value);
});
</script>


<template>
  <BaseLayout>
    <div class="container">
      <h2>Livros do Autor: {{ author?.nome || 'Autor não encontrado' }}</h2>

      <Spinner v-if="isLoading" />

      <BooksCards v-else :livros="books" />
    </div>
  </BaseLayout>
</template>


<style scoped>
h2 {
  color: var(--color_4);
  text-align: center;
  font-weight: bold;
  margin-bottom: 30px;
}
</style>