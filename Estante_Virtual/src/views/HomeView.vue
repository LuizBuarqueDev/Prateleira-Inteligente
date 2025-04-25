<script setup>
import { ref, computed, onMounted, provide } from 'vue';
import BaseLayout from '@/components/BaseLayout.vue';
import HeaderTemplate from '@/components/HeaderTemplate.vue';
import BookCard from '@/components/BooksCards.vue';
import Spinner from '@/components/Spinner.vue';

import livroService from '@/services/LivroService';

const livros = ref([]);
const isLoading = ref(true);

const searchValue = ref('');
provide('searchValue', searchValue);

const livrosFiltrados = computed(() =>
  livros.value.filter(livro =>
    livro.titulo?.toLowerCase().includes(searchValue.value.toLowerCase())
  )
);

const fetchLivros = async () => {
  isLoading.value = true;
  const response = await livroService.findAll();
  livros.value = response.data; // Certifique-se que o axios está retornando os dados aqui
  isLoading.value = false;
};

onMounted(fetchLivros);
</script>


<template>
  <HeaderTemplate />

  <BaseLayout>
    <div v-if="isLoading">
      <Spinner />
    </div>

    <div v-else>
      <div class="shelf-title">
        <i class="fa-solid fa-medal"></i>
        <span>Destaques</span>
      </div>

      <section class="shelf">
        <BookCard :livros="livrosFiltrados" />
      </section>
    </div>
  </BaseLayout>
</template>


<style scoped>
.shelf-title {
  padding: 20px;
  color: var(--color_4);

  i {
    margin: 10px;
    font-size: 1.5em;
  }

  span {
    margin-left: 10px;
    font-weight: bold;
    font-size: 1.5em;
  }
}
</style>