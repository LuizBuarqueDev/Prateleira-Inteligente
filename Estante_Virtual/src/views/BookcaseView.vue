<script setup>
import { ref, onMounted } from 'vue';
import DAOService from '@/services/DAOService';
import { auth } from '@/assets/js/firebase';

import BaseLayout from '@/components/BaseLayout.vue';
import BooksCards from '@/components/BooksCards.vue';

//DAOs
const userBooksService = new DAOService('user_books');
const bookService = new DAOService('books');

const userBooks = ref([]);

const fetchUserBooks = async () => {
    const user = auth.currentUser;

    if (user) {
        const userBooksEntries = await userBooksService.search('uid', user.uid);
        // Extração dos IDs dos livros
        const bookIds = userBooksEntries.map(entry => entry.book);
        // Busca os detalhes de cada livro associado
        userBooks.value = await Promise.all(bookIds.map(id => bookService.get(id)));
    };
}
onMounted(fetchUserBooks);
</script>


<template>
    <BaseLayout>
        <div class="container-bookcase">
            <div class="section-title">
                <h2><i class="fa-solid fa-flag"></i>Livros na Estante</h2>
            </div>
            <RouterLink to="/bookregistration">
                <button class="btn">
                    <i class="fa-solid fa-plus"></i> Cadastrar livro
                </button>
            </RouterLink>
            <section class="book-grid d-flex">
                <BooksCards :livros="userBooks" />
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
}
</style>
