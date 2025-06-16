<script setup>
import { ref, onMounted } from 'vue';
import PrateleiraService from '@/services/PrateleiraService';
import livroService from '@/services/LivroService';
import BaseLayout from '@/components/BaseLayout.vue';
import BooksCards from '@/components/BooksCards.vue';
import Spinner from '@/components/Spinner.vue';
import AuthService from '@/services/AuthService';

const userBooks = ref([]);
const isLoading = ref(true);
const userId = AuthService.getUserId();

const fetchUserBooks = async () => {
  try {
    isLoading.value = true;
    const response = await PrateleiraService.listarLivrosPorUsuario(userId);

    if (response.data && response.data.length > 0) {
      const bookIds = response.data.map(item => item.idLivro);
      const livrosResponse = await livroService.findAllByIds(bookIds);
      userBooks.value = livrosResponse.data;
    } else {
      userBooks.value = [];
    }
  } catch (error) {
    console.error('Erro ao carregar estante:', error);
    alert('Não foi possível carregar sua estante');
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  fetchUserBooks();
});
</script>


<template>
  <BaseLayout>
    <div class="container-bookcase">
      <div class="section-title">
        <h2><i class="fa-solid fa-flag"></i>Minha Estante</h2>
      </div>

      <RouterLink to="/bookregistration">
        <button class="btn">
          <i class="fa-solid fa-plus"></i> Cadastrar livro
        </button>
      </RouterLink>

      <Spinner v-if="isLoading" />

      <section v-else class="book-grid d-flex">
        <BooksCards :livros="userBooks" />

        <div v-if="!isLoading && userBooks.length === 0" class="empty-estante">
          <p>Sua estante está vazia. Adicione livros para começar!</p>
        </div>
      </section>
    </div>
  </BaseLayout>
</template>

<style scoped>
.container-bookcase {
    padding: 20px;

    .section-title {
        padding: 20px 0;
        color: var(--color_4);
        display: flex;
        align-items: center;
        font-size: 1.5em;

        i {
            margin-right: 10px;
            color: var(--color_4);
        }
    }

    .book-grid {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        gap: 20px;
        padding-bottom: 40px;
    }
  .empty-estante {
    width: 100%;
    text-align: center;
    padding: 50px;
    font-size: 1.2em;
    color: var(--color_4);
  }}
</style>
