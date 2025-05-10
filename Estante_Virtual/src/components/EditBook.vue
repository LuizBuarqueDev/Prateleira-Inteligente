<script setup>
import { ref } from 'vue'
import BaseModal from './base_layout/BaseModal.vue'

const showModal = ref(false)
const modalContent = ref('')
const modalSize = ref('md')

const openEditModal = () => {
  modalContent.value = 'edit'
  modalSize.value = 'md'
  showModal.value = true
}

const openDeleteModal = () => {
  modalContent.value = 'delete'
  modalSize.value = 'sm'
  showModal.value = true
}
</script>

<template>
  <div class="edit-bar">
    <i class="fa-solid fa-pen-to-square" @click="openEditModal"></i>
    <i class="fa-solid fa-trash" @click="openDeleteModal"></i>
  </div>

  <BaseModal :isOpen="showModal" :onClose="() => showModal = false" :size="modalSize">
    <template v-if="modalContent === 'edit'">
      <h2 class="text-xl font-bold mb-4">Editar Item</h2>
      <p>Formulário de edição aqui...</p>
    </template>
    <template v-else-if="modalContent === 'delete'">
      <h2 class="text-xl font-bold mb-4 text-red-600">Confirmar Exclusão</h2>
      <p>Tem certeza que deseja excluir este item?</p>
    </template>
  </BaseModal>
</template>

<style scoped>
.edit-bar {
  display: flex;
  flex-direction: column;
}
.edit-bar i {
  padding: 10px;
  cursor: pointer;
}
.edit-bar i:hover {
  transform: scale(1.3);
  color: #bb0000;
}
</style>
