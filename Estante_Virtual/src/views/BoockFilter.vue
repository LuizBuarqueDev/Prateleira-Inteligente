<script setup>
import { ref } from 'vue';
import livroService from '@/services/LivroService';

const emit = defineEmits(['filtered']);

const minRating = ref(0);
const sortBy = ref('');

const applyFilters = async () => {
  try {
    const response = await livroService.filter(minRating.value, sortBy.value);
    emit('filtered', response.data);
  } catch (error) {
    console.error('Erro ao filtrar livros:', error);
  }
};
</script>

<template>
  <div class="book-filter">
    <div class="filter-group">
      <label>Avaliação mínima:</label>
      <select v-model="minRating" @change="applyFilters">
        <option value="0">Qualquer</option>
        <option value="3">3+ estrelas</option>
        <option value="4">4+ estrelas</option>
      </select>
    </div>

    <div class="filter-group">
      <label>Ordenar por:</label>
      <select v-model="sortBy" @change="applyFilters">
        <option value="">Padrão</option>
        <option value="rating">Melhores avaliados</option>
        <option value="title">Título (A-Z)</option>
        <option value="date">Mais recentes</option>
      </select>
    </div>
  </div>
</template>

<style scoped>
.book-filter {
  display: flex;
  gap: 20px;
  margin: 20px 0;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 10px;
}

select {
  padding: 5px;
  border-radius: 4px;
  border: 1px solid #ccc;
}
</style>