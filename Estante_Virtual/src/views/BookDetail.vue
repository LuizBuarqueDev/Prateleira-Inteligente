<script setup>
import { ref, onMounted, watch, computed } from 'vue';
import { useRoute } from 'vue-router';
import livroService from '@/services/LivroService';
import PrateleiraService from '@/services/PrateleiraService';
import { modelLivro } from '@/models/modelLivro';
import BaseLayout from '@/components/BaseLayout.vue';
import EditBook from '@/components/EditBook.vue';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import AuthService from '@/services/AuthService';
import StarRating from '@/components/StarRating.vue';

// Variáveis reativas
const date = ref(null);
const livro = ref(modelLivro());
const route = useRoute();
const estaNaEstante = ref(false);
const similarBooks = ref([]);
const isLoading = ref(true);

// Computed properties
const userId = computed(() => AuthService.getUserId());
const totalAvaliacoes = computed(() => livro.value.usuariosLivros?.filter(ul => ul.nota !== null).length || 0);
const avaliacaoUsuario = computed(() => {
  return livro.value.usuariosLivros?.find(ul => ul.usuario?.id === userId.value)?.nota || 0;
});

// Funções
const fetchLivro = async () => {
  try {
    const response = await livroService.findById(route.params.id);
    livro.value = response.data;
  } catch (error) {
    console.error('Erro ao carregar livro:', error);
  }
};

const handleLivroAtualizado = async () => {
  await fetchLivro();
  await loadSimilarBooks();
};

const loadSimilarBooks = async () => {
  try {
    const response = await livroService.findSimilarBooks(route.params.id);
    similarBooks.value = response.data;
  } catch (error) {
    console.error('Erro ao carregar livros similares:', error);
  }
};

const verificarSeEstaNaEstante = async () => {
  if (AuthService.isAuthenticated.value) {
    try {
      const response = await PrateleiraService.buscarLivroNaPrateleira(userId.value, route.params.id);
      estaNaEstante.value = !!response.data;
    } catch (error) {
      console.error('Erro ao verificar estante:', error);
      estaNaEstante.value = false;
    }
  }
};

const toggleEstante = async () => {
  try {
    if (estaNaEstante.value) {
      await PrateleiraService.removerLivro(userId.value, route.params.id);
    } else {
      await PrateleiraService.adicionarLivro({
        idUsuario: userId.value,
        idLivro: route.params.id
      });
    }
    estaNaEstante.value = !estaNaEstante.value;
    await fetchLivro(); // Recarrega os dados do livro após alteração
  } catch (error) {
    console.error('Erro ao atualizar estante:', error);
    alert('Ocorreu um erro ao atualizar sua estante');
  }
};

// Watchers
watch(livro, (newLivro) => {
  if (newLivro.anoPublicacao) {
    const [year, month, day] = newLivro.anoPublicacao.split('-').map(Number);
    date.value = new Date(year, month - 1, day);
  } else {
    date.value = null;
  }
}, { immediate: true });

watch(() => route.params.id, async (newId) => {
  if (newId) {
    await loadBookData();
  }
});

// Carregamento inicial
const loadBookData = async () => {
  isLoading.value = true;
  try {
    await fetchLivro();
    await verificarSeEstaNaEstante();
    await loadSimilarBooks();
  } finally {
    isLoading.value = false;
  }
};

onMounted(loadBookData);
</script>

<template>
  <BaseLayout>
    <template v-if="isLoading">
      <div class="text-center py-5">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Carregando...</span>
        </div>
      </div>
    </template>

    <template v-else>
      <section class="row book-detail">
        <aside class="col-12 col-sm-4 d-flex">
          <div class="d-flex flex-column align-items-center">
            <img :src="livro.capa" @error="livro.capa = '/img/loading.gif'" 
                 alt="Capa do livro" class="book-image" />

            <button v-show="AuthService.isAuthenticated.value" 
                    @click="toggleEstante" 
                    class="btn btn-estante mt-3"
                    :class="estaNaEstante ? 'btn-danger' : 'btn-primary'">
              {{ estaNaEstante ? 'Remover da Estante' : 'Adicionar à Estante' }}
            </button>
          </div>

          <div class="ms-3 d-flex align-items-start">
            <EditBook :livro="livro" @livroAtualizado="handleLivroAtualizado" />
          </div>
        </aside>

        <div class="col-12 col-sm-8">
          <h2>{{ livro.titulo || 'Título não disponível' }}</h2>
          
          <div class="rating-section mb-3">
            <StarRating :livro-id="livro.id" 
                       :user-id="userId"
                       :initial-rating="avaliacaoUsuario"
                       @rated="fetchLivro" />
            
            <div v-if="livro.mediaAvaliacoes > 0" class="rating-average ms-2">
              {{ livro.mediaAvaliacoes.toFixed(1) }} ({{ totalAvaliacoes }} avaliações)
            </div>
          </div>

          <p><strong>Autor(es):</strong> {{ livro.autor?.nome || 'Não informado' }}</p>

          <p><strong>Categoria(s):</strong>
            <span v-for="(categoria, index) in livro.nomesCategorias" :key="index">
              <RouterLink :to="{ name: 'CategoryBooks', params: { categoriaId: livro.idCategorias[index], nomeCategoria: categoria } }"
                         class="category-link">
                {{ categoria }}{{ index < livro.nomesCategorias.length - 1 ? ', ' : '' }}
              </RouterLink>
            </span>
            <span v-if="!livro.nomesCategorias?.length">Não informado</span>
          </p>

          <p><strong>Descrição:</strong> {{ livro.descricao || 'Descrição indisponível' }}</p>
          
          <p><strong>Data de Publicação:</strong>
            <VueDatePicker v-model="date" 
                          placeholder="Selecione a data de publicação"
                          :format="(d) => d ? d.toLocaleDateString('pt-BR') : ''" 
                          :disabled="true" />
          </p>
          
          <p><strong>Editora:</strong> {{ livro.editora || 'Não informado' }}</p>
        </div>
      </section>

      <section v-if="similarBooks.length" class="similar-books mt-5">
        <h3 class="mb-4">Livros similares</h3>
        <BooksCards :livros="similarBooks" />
      </section>
    </template>
  </BaseLayout>
</template>

<style scoped>
.book-detail {
  margin: 30px;
}

.book-image {
  height: 500px;
  width: 350px;
  border-radius: 8px;
  border: 2px solid var(--color_4);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  object-fit: cover;
}

.btn-estante {
  width: 200px;
  height: 50px;
  font-size: 1.2em;
  font-weight: bold;
}

.category-link {
  text-decoration: none;
  margin-right: 5px;
  padding: 2px 6px;
  color: var(--color_4);
  border-radius: 10px;
  border: 1px solid var(--color_4);
  font-weight: bold;
  transition: all 0.3s ease;
}

.category-link:hover {
  background-color: var(--color_4);
  color: var(--color_white);
}

.rating-section {
  display: flex;
  align-items: center;
  padding: 10px 0;
}

.rating-average {
  font-size: 0.9em;
  color: #666;
}

.similar-books {
  padding-top: 20px;
  border-top: 1px solid #eee;
}

@media (max-width: 768px) {
  .book-image {
    height: 350px;
    width: 250px;
  }
  
  .btn-estante {
    width: 180px;
    height: 45px;
    font-size: 1em;
  }
}
</style>