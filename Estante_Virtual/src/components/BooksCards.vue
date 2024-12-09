    <script setup>
    import { useRouter } from 'vue-router';

    const defaultImage = "/img/bookImg.png";
    const router = useRouter();

    const props = defineProps({
      books: {
        type: Array,
        required: true,
      },
    });

    const getBookImage = (imageLink) => {
      return imageLink && imageLink !== "N/A" ? imageLink : defaultImage;
    };

    const goToBookDetail = (book) => {
      router.push({ name: 'BookDetail', params: { id: book.id } });
    };

</script>
<template>
  <div v-if="books.length" class="book-container">
    <div v-for="book in books" :key="book.id" class="book-item" @click="goToBookDetail(book)">
      <span class="title">{{ book.title || 'Título não disponível' }}</span>
      <img :src="getBookImage(book.image_link)" alt="Capa do livro" />
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

  .book-item {
    position: relative;
    margin: 25px;
    border: 2px solid red;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 3px 3px 4px rgba(0, 0, 0, 0.74);

    img {
      width: 180px;
      height: 250px;
      transition: 0.3s;
    }

    &:hover .title {
      opacity: 1;
    }

    &:hover img {
      filter: brightness(40%) blur(2px);
    }
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
    z-index: 1;
  }
}

.no-books-found {
  margin: 30px;
}
</style>
