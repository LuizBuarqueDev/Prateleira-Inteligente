<script setup>
import { ref, onMounted, defineEmits, watch } from 'vue';

import Multiselect from 'vue-multiselect';
import 'vue-multiselect/dist/vue-multiselect.min.css';

import BaseModal from './base_layout/BaseModal.vue';

import AutorService from '@/services/AutorService';

const emit = defineEmits(['update:selectedAuthorId']);

const authors_list = ref([]);
const selectedAuthor = ref(null);

const showModal = ref(false);
const modalSize = ref('md');

const newAuthorName = ref('');

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

const cadastrarAutor = async (event) => {
  event.preventDefault(); // evita submit/reload

  if (!newAuthorName.value.trim()) {
    alert('Informe o nome do autor.');
    return;
  }

  try {
    // Chama o serviço de criação do autor
    await AutorService.create({ nome: newAuthorName.value });

    // Limpa o input
    newAuthorName.value = '';
    // Fecha modal
    showModal.value = false;

    // Atualiza a lista
    await fetchAutores();
  } catch (error) {
    console.error('Erro ao cadastrar autor:', error);
  }
};
</script>

<template>
  <div class="d-flex">
    <Multiselect
      v-model="selectedAuthor"
      :options="authors_list"
      label="nome"
      track-by="id"
      placeholder="Selecione o autor"
      search-placeholder="Digite para buscar..."
      no-options-text="Nenhum autor encontrado"
    />
    <button class="btn mx-3" @click="showModal = true">
      <i class="fa-solid fa-plus"></i>
    </button>

    <BaseModal :isOpen="showModal" :onClose="() => showModal = false" :size="modalSize">
      <div class="m-4">
        <h5>Cadastrar novo autor</h5>
        <input
          v-model="newAuthorName"
          type="text"
          placeholder="Nome do autor"
          class="form-control mb-3"
        />
        <!-- Usando .prevent para evitar submit padrão -->
        <button class="btn mt-3" type="button" @click="cadastrarAutor" :disabled="!newAuthorName.trim()">Cadastrar</button>
      </div>
    </BaseModal>
  </div>
</template>

<style scoped>
button {
  border: 1px solid red;
  transition: all 0.3s ease;
}

button:hover {
  border: 1px solid red;
  transform: scale(1.1);
}
</style>
