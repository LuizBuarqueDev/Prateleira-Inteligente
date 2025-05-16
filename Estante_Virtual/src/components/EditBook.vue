<script setup>
import { ref, computed, watch } from 'vue'
import router from '@/router'

import BaseModal from './base_layout/BaseModal.vue'
import LivroService from '@/services/LivroService'

import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'


const props = defineProps({
  livro: {
    type: Object,
    required: true
  }
})

const date = ref(null);
const showModal = ref(false)
const modalContent = ref('');
const modalSize = ref('md');
const livroEditavel = ref({ ...props.livro })

const idAutor = computed({
  get() {
    return livroEditavel.value.idAutor?.[0] || ''
  },
  set(value) {
    if (!livroEditavel.value.idAutor) {
      livroEditavel.value.idAutor = []
    }
    livroEditavel.value.idAutor[0] = value
  }
})

const idCategorias = computed({
  get() {
    return livroEditavel.value.idCategorias?.[0] || ''
  },
  set(value) {
    if (!livroEditavel.value.idCategorias) {
      livroEditavel.value.idCategorias = []
    }
    livroEditavel.value.idCategorias[0] = value
  }
})

const isTituloValido = computed(() => livroEditavel.value.titulo && livroEditavel.value.titulo.trim() !== '')
const isDataValida = computed(() => {
  const data = new Date(livroEditavel.value.anoPublicacao)
  return !isNaN(data.getTime()) && livroEditavel.value.anoPublicacao.length === 10
})

const formatarData = (dataString) => {
  if (!dataString) return 'N/D'
  const data = new Date(dataString)
  if (isNaN(data.getTime())) return 'N/D';
  const options = { year: 'numeric', month: 'long', day: 'numeric' }
  return data.toLocaleDateString('pt-BR', options);
}

const validarData = (dataString) => {
  const data = new Date(dataString)
  if (isNaN(data.getTime())) {
    console.error('Data inválida')
    return '';
  }
  return data.toISOString().split('T')[0];
}

// Sincroniza a data do livro para o datepicker
watch(() => props.livro.anoPublicacao, (novaData) => {
  if (novaData) {
    const d = new Date(novaData);
    date.value = isNaN(d.getTime()) ? null : d;
  } else {
    date.value = null;
  }
}, { immediate: true });

// Quando o datepicker muda, atualiza o livroEditavel.anoPublicacao
watch(date, (novaDate) => {
  if (novaDate instanceof Date && !isNaN(novaDate.getTime())) {
    livroEditavel.value.anoPublicacao = novaDate.toISOString().split('T')[0];
  } else {
    livroEditavel.value.anoPublicacao = '';
  }
});

watch(() => props.livro, (novoLivro) => {
  livroEditavel.value = {
    ...novoLivro,
    idAutor: novoLivro.idAutor || [],
    idCategorias: novoLivro.idCategorias || []
  }
})

const openEditModal = () => {
  modalContent.value = 'edit';
  modalSize.value = 'xl';
  showModal.value = true;
}

const openDeleteModal = () => {
  modalContent.value = 'delete';
  modalSize.value = 'xl';
  showModal.value = true;
}

const confirmDelete = () => {
  LivroService.delete(livroEditavel.value.id);
  router.push('/');
  alert('Livro excluído!');
  showModal.value = false;
}

const emit = defineEmits(['livroAtualizado']);

const salvarEdicao = async () => {
  try {
    livroEditavel.value.idAutor = Number(idAutor.value); // ← Corrige aqui
    livroEditavel.value.idCategorias = [Number(idCategorias.value)]; // ← Certifique-se que isso é um array de números

    if (livroEditavel.value.anoPublicacao) {
      livroEditavel.value.anoPublicacao = validarData(livroEditavel.value.anoPublicacao);
    }

    await LivroService.update(livroEditavel.value.id, livroEditavel.value);
    emit('livroAtualizado', livroEditavel.value);
    showModal.value = false;
  } catch (error) {
    console.error('Erro ao atualizar:', error);
  }
}
</script>

<template>
  <div class="edit-bar">
    <i class="fa-solid fa-pen-to-square" @click="openEditModal"></i>
    <i class="fa-solid fa-trash" @click="openDeleteModal"></i>
  </div>

  <BaseModal :isOpen="showModal" :onClose="() => showModal = false" :size="modalSize">
    <!-- Modal de Edição com INPUTS -->
    <template v-if="modalContent === 'edit'">
      <h2 class="text-xl font-bold mb-4">
        <i class="fa-solid fa-pen-to-square"></i>
        Editar Livro
      </h2>
      <form class="grid gap-4">
        <input v-model="livroEditavel.titulo" type="text" placeholder="Título" class="input" />
        <input v-model="idAutor" type="text" placeholder="Autor" class="input" />
        <input v-model="idCategorias" type="text" placeholder="Categoria" class="input" />
        <textarea v-model="livroEditavel.descricao" placeholder="Descrição" class="input" rows="4"></textarea>

        <VueDatePicker 
          v-model="date" 
          placeholder="Selecione a data de publicação"
          :format="(d) => d ? d.toLocaleDateString('pt-BR') : ''" 
          />

        <input v-model="livroEditavel.editora" type="text" placeholder="Editora" class="input" />

        <div class="d-flex justify-content-between mt-3">
          <button type="button" class="btn btn-danger" @click="salvarEdicao"
            :disabled="!isTituloValido || !isDataValida">Salvar</button>
          <button type="button" class="btn btn-secondary" @click="showModal = false">Cancelar</button>
        </div>
      </form>
    </template>

    <!-- Modal de Exclusão -->
    <template v-else-if="modalContent === 'delete'">
      <div class="text-center">
        <h2 class="text-xl font-bold mb-4 text-red-600">Confirmar Exclusão</h2>
        <p class="mb-4">Tem certeza que deseja excluir o livro <strong>{{ livroEditavel.titulo }}</strong>?</p>
        <div class="d-flex justify-content-between mt-5">
          <button class="btn btn-danger" @click="confirmDelete">Confirmar</button>
          <button class="btn btn-secondary ml-2" @click="showModal = false">Cancelar</button>
        </div>
      </div>
    </template>
  </BaseModal>
</template>

<style scoped>
.edit-bar {
  display: flex;
  flex-direction: column;

  i {
    padding: 10px;
    cursor: pointer;
  }

  i:hover {
    transform: scale(1.3);
    color: #bb0000;
  }
}

.input {
  margin: 5px;
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
}
</style>