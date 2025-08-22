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
    <section class="similar-books container">
        <h2 class="">Livros Similares</h2>
        <div class=" mb-5">
            <p v-if="!similarBooks.length">Nenhum livro similar encontrado.</p>

            <div v-else>
                <BooksCards :livros="similarBooks" />
            </div>

        </div>
    </section>
</template>


<style>
.similar-books {

    h2 {
        text-shadow: 0 4px 6px rgba(0, 0, 0, 0.5);
        text-align: center;
        font-weight: bold;
        font-size: 2.8rem;
        margin: 50px;
        color: var(--color_4);
    }
}
</style>