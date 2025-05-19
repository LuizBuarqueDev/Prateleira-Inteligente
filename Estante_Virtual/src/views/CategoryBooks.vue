<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import livroService from '@/services/LivroService';
import BaseLayout from '@/components/BaseLayout.vue';
import BooksCards from '@/components/BooksCards.vue';
import Spinner from '@/components/Spinner.vue';

const route = useRoute();
const livros = ref([]);
const isLoading = ref(true);
const nomeCategoria = ref(route.params.nomeCategoria);

const fetchLivrosPorCategoria = async () => {
  try {
    isLoading.value = true;
    const response = await livroService.findByCategoriaId(route.params.categoriaId);
    livros.value = response.data;
  } catch (error) {
    console.error('Erro ao carregar livros:', error);
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  fetchLivrosPorCategoria();
});
</script>

<template>
  <BaseLayout>
    <div class="container">
      <div class="category-header">
        <h2>Livros da categoria: {{ nomeCategoria }}</h2>
      </div>

      <Spinner v-if="isLoading" />

      <BooksCards v-else :livros="livros" />
    </div>
  </BaseLayout>
</template>

<style scoped>
.container {
  padding: 20px;
}

.category-header {
  padding: 20px 0;
  color: var(--color_4);
  text-align: center;
  margin-bottom: 30px;

  h2 {
    font-weight: bold;
    font-size: 1.8em;
  }
}
</style>