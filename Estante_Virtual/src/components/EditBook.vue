<script setup>
import { ref, computed, watch } from 'vue'
import BaseModal from './base_layout/BaseModal.vue'

// Recebe o livro como prop
const props = defineProps({
  livro: {
    type: Object,
    required: true
  }
})

const showModal = ref(false)
const modalContent = ref('')
const modalSize = ref('md')

// Cópia reativa do livro recebido (para edição sem afetar o original diretamente)
const livroEditavel = ref({ ...props.livro })

// Computed properties para idAutor e idCategorias
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

// Computed para validar o título e a data
const isTituloValido = computed(() => livroEditavel.value.titulo && livroEditavel.value.titulo.trim() !== '')
const isDataValida = computed(() => {
  const data = new Date(livroEditavel.value.anoPublicacao)
  return !isNaN(data.getTime()) && livroEditavel.value.anoPublicacao.length === 10
})

// Função para formatar a data para exibição
const formatarData = (dataString) => {
  if (!dataString) return 'N/D'
  const data = new Date(dataString)
  if (isNaN(data.getTime())) return 'N/D' // Verifica se a data é inválida
  const options = { year: 'numeric', month: 'long', day: 'numeric' }
  return data.toLocaleDateString('pt-BR', options) // Exibe no formato: 1 de Janeiro de 2020
}

// Função para validar e formatar a data no formato correto (YYYY-MM-DD)
const validarData = (dataString) => {
  const data = new Date(dataString)
  if (isNaN(data.getTime())) {
    console.error('Data inválida')
    return '' // Retorna uma string vazia ou você pode retornar um valor default como "N/D"
  }
  // Se a data for válida, retorna no formato correto (YYYY-MM-DD)
  return data.toISOString().split('T')[0] // Retorna apenas a data no formato correto
}

// Sempre que a prop mudar, atualiza a cópia reativa
watch(() => props.livro, (novoLivro) => {
  livroEditavel.value = {
    ...novoLivro,
    idAutor: novoLivro.idAutor || [], // Inicializando com um array vazio
    idCategorias: novoLivro.idCategorias || [] // Inicializando com um array vazio
  }
})

const openEditModal = () => {
  modalContent.value = 'edit'
  modalSize.value = 'xl'
  showModal.value = true
}

const openDeleteModal = () => {
  modalContent.value = 'delete'
  modalSize.value = 'xl'
  showModal.value = true
}

const confirmDelete = () => {
  console.log('Livro excluído!')
  showModal.value = false
}

const salvarEdicao = () => {
  // Validar e armazenar a data antes de salvar
  if (livroEditavel.value.anoPublicacao) {
    livroEditavel.value.anoPublicacao = validarData(livroEditavel.value.anoPublicacao)
  }
  console.log('Livro atualizado:', livroEditavel.value)
  showModal.value = false
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
        <input v-model="livroEditavel.anoPublicacao" type="text" placeholder="Ano de Publicação (YYYY-MM-DD)"
          class="input" />
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
        <button class="btn btn-danger" @click="confirmDelete">Confirmar</button>
        <button class="btn btn-secondary ml-2" @click="showModal = false">Cancelar</button>
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