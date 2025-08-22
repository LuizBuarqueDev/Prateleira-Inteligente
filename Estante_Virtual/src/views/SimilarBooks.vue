<script setup>
import BooksCards from '@/components/BooksCards.vue';
import LivroService from '@/services/LivroService';
import { onMounted, ref } from 'vue';

const props = defineProps({
  bookId: {
    type: Number,
    required: true
  }
});

const similarBooks = ref([]);

const findSimilarBooks = async () => {
    const response = await LivroService.findSimilarBooks(props.bookId);
    similarBooks.value = response.data;
}

onMounted(() => {
    findSimilarBooks();
});
</script>


<template>
    <section>
        <h2 class="">Livros Similares</h2>
        <div class="similar-books mb-5">
            <p v-if="!similarBooks.length">Nenhum livro similar encontrado.</p>

            <div v-else>
                <BooksCards :livros="similarBooks" />
            </div>

        </div>
    </section>
</template>


<style>
h2 {
    text-align: center;
    font-size: 2.8rem;
    margin: 50px;
    color: var(--color-2);
}
</style>