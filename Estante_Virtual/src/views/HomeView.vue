<script setup>
import { ref, computed, onMounted, provide } from 'vue';
import BaseLayout from '@/components/BaseLayout.vue';
import HeaderTemplate from '@/components/HeaderTemplate.vue';
import BookCard from '@/components/BooksCards.vue';
import DAOService from '@/services/DAOService';
import Spinner from '@/components/Spinner.vue';

const bookService = new DAOService('books');

const books = ref([]);
const isLoading = ref(true);

const searchValue = ref('');
provide('searchValue', searchValue)


const highlightedBooks = computed(() =>
  books.value.filter(book =>
    book.title.toLowerCase().includes(searchValue.value.toLowerCase())
  )
);

const fetchBooks = async () => {
  isLoading.value = true;
  books.value = await bookService.getAll();
  isLoading.value = false;
};

onMounted(fetchBooks);
</script>


<template>
  <HeaderTemplate />

  <BaseLayout>
    <div v-if="isLoading">
      <Spinner />
    </div>

    <div v-else>
      <div class="shelf-title">
        <i class="fa-solid fa-medal"></i>
        <span>Destaques</span>
      </div>

      <section class="shelf">
        <BookCard :books="highlightedBooks" />
      </section>
    </div>
  </BaseLayout>
</template>



<style scoped>
.shelf-title {
  padding: 20px;
  color: var(--color_4);

  i {
    margin: 10px;
    font-size: 1.5em;
  }

  span {
    margin-left: 10px;
    font-weight: bold;
    font-size: 1.5em;
  }
}
</style>
