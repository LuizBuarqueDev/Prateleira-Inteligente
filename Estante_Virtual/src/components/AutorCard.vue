<script setup>
import { ref, onMounted, defineEmits, watch } from 'vue';
import Multiselect from 'vue-multiselect';
import 'vue-multiselect/dist/vue-multiselect.min.css';
import AutorService from '@/services/AutorService';

const emit = defineEmits(['update:selectedAuthorId']);

const authors_list = ref([]);
const selectedAuthor = ref(null);

const fetchAutores = async () => {
  try {
    const response = await AutorService.findAll();
    authors_list.value = response.data;
  } catch (error) {
    console.error('Erro ao buscar autores:', error);
  }
};

onMounted(fetchAutores);

watch(selectedAuthor, (newVal) => {
  if (newVal) {
    emit('update:selectedAuthorId', newVal.id);
  } else {
    emit('update:selectedAuthorId', null);
  }
});
</script>

<template>
  <Multiselect
    v-model="selectedAuthor"
    :options="authors_list"
    label="nome"
    track-by="id"
    placeholder="Selecione o autor"
    search-placeholder="Digite para buscar..."
    no-options-text="Nenhum autor encontrado"
  />
</template>


<style scoped>
button {
  border: 1px solid red;
}
</style>
