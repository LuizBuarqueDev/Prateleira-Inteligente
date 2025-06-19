<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const props = defineProps({
  livros: {
    type: Array,
    required: true,
  },
});

const goToLivroDetail = (livro) => {
  router.push({ name: 'BookDetail', params: { id: livro.id } });
};
</script>

<template>
  <div v-if="livros.length" class="book-container">
    <div
      v-for="livro in livros"
      :key="livro.id"
      class="book-item"
      @click="goToLivroDetail(livro)"
    >
      <span class="title">{{ livro.titulo || 'Título não disponível' }}</span>
      <img :src="livro.capa" @error="livro.capa = '/img/loading.gif'" alt="Capa do livro" />
    </div>
  </div>

  <div v-else>
    <p class="no-books-found">Nenhum livro disponível.</p>
  </div>
</template>

<style scoped>
.book-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.book-item {
  position: relative;
  margin: 15px;
  border: 2px solid red;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 3px 3px 4px rgba(0, 0, 0, 0.74);
  cursor: pointer;
}

.book-item img {
  width: 180px;
  height: 250px;
  transition: 0.3s;
  display: block;
}

.book-item:hover .title {
  opacity: 1;
}

.book-item:hover img {
  filter: brightness(40%) blur(2px);
}

.title {
  font-size: 1.2em;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: var(--color_white);
  opacity: 0;
  transition: opacity 0.3s ease;
  z-index: 2;
  text-align: center;
  width: 100%;
  padding: 0 10px;
}

.no-books-found {
  margin: 30px;
}
</style>