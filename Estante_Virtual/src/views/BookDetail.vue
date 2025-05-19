<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';

import livroService from '@/services/LivroService';
import PrateleiraService from '@/services/PrateleiraService';

import { modelLivro } from '@/models/modelLivro';

import BaseLayout from '@/components/BaseLayout.vue';
import EditBook from '@/components/EditBook.vue';

import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'


const date = ref(null);
const livro = ref(modelLivro());
const route = useRoute();
const estaNaEstante = ref(false);
const userId = "1"; // ID do usuário fixo


watch(livro, (newLivro) => {
  if (newLivro.anoPublicacao) {
    date.value = new Date(newLivro.anoPublicacao);
  } else {
    date.value = null;
  }
}, { immediate: true });


const verificarSeEstaNaEstante = async () => {
  try {
    const response = await PrateleiraService.buscarLivroNaPrateleira(userId, route.params.id);
    estaNaEstante.value = response.data !== null;
  } catch (error) {
    console.error('Erro ao verificar estante:', error);
  }
};

const toggleEstante = async () => {
  const livroId = route.params.id;

  try {
    if (estaNaEstante.value) {
      await PrateleiraService.removerLivro(userId, livroId);
    } else {
      await PrateleiraService.adicionarLivro({
        idUsuario: userId,
        idLivro: livroId
      });
    }
    estaNaEstante.value = !estaNaEstante.value;
  } catch (error) {
    console.error('Erro ao atualizar estante:', error);
    alert('Ocorreu um erro ao atualizar sua estante');
  }
};

const fechLivro = async () => {
  const response = await livroService.findById(route.params.id);
  livro.value = response.data;
}

onMounted(async () => {
  try {
    await fechLivro();
    await verificarSeEstaNaEstante();
  } catch (error) {
    console.error('Erro ao carregar livro:', error);
  }
});
</script>


<template>
  <BaseLayout>
    <section class="row book-detail">
      <aside class="col-12 col-sm-4 d-flex">
        <!-- Coluna da imagem + botão -->
        <div class="d-flex flex-column align-items-center">
          <img :src="livro.capa" alt="Capa do livro" class="book-image" />
          <button @click="toggleEstante" class="btn btn-estante mt-3"
            :class="estaNaEstante ? 'btn-danger' : 'btn-primary'">
            {{ estaNaEstante ? 'Remover da Estante' : 'Adicionar à Estante' }}
          </button>
        </div>

        <!-- Coluna dos ícones (EditBook) -->
        <div class="ms-3 d-flex align-items-start">
          <EditBook :livro="livro" @livroAtualizado="fechLivro" />
        </div>
      </aside>

      <div class="col-12 col-sm-8">
        <h2>{{ livro.titulo || 'Título não disponível' }}</h2>
        <p><strong>Autor(es):</strong> {{ livro.idAutor?.join(', ') || 'Não informado' }}</p>


        <p><strong>Categoria(s):</strong>
          <span v-for="(categoria, index) in livro.nomesCategorias" :key="index">
            <RouterLink
               :to="{ name: 'CategoryBooks', params: { categoriaId: livro.idCategorias[index], nomeCategoria: categoria } }"
               class="category-link"
            >
              {{ categoria }}{{ index < livro.nomesCategorias.length - 1 ? ', ' : '' }}
           </RouterLink>
          </span>
          <span v-if="!livro.nomesCategorias?.length">Não informado</span>
        </p>

        <p><strong>Descrição:</strong> {{ livro.descricao || 'Descrição indisponível' }}</p>
        <p><strong>Data de Publicação:</strong>
          <VueDatePicker v-model="date" placeholder="Selecione a data de publicação"
            :format="(d) => d ? d.toLocaleDateString('pt-BR') : ''" :disabled="true" />
        </p>
        <p><strong>Editora:</strong> {{ livro.editora || 'Não informado' }}</p>
      </div>
    </section>
  </BaseLayout>
</template>

<style scoped>
.book-detail {
  margin: 50px;
}

.custom-btn {
  margin: 10px;
  background-color: var(--color_1);
  color: var(--color_white);
}

.book-image {
  height: 500px;
  width: 350px;
  border-radius: 8px;
  border: 2px solid red;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);

.category-link {
  color: var(--color_4);
  text-decoration: none;
  transition: color 0.3s ease;
  }

.category-link:hover {
  color: var(--color_1_hover);
  text-decoration: underline;
  }
}
</style>