<script setup>
import { ref } from 'vue';
import BaseLayout from '@/components/BaseLayout.vue';
import DAOService from '@/services/DAOService';
import router from '@/router';

const bookService = new DAOService('books');
const imagePreview = '/img/bookImg.png';

const book = ref({
    title: '',
    authors: [],
    categories: [],
    description: '',
    publisher: '',
    page_count: null,
    published_date: null,
    language: '',
    image_link: imagePreview // Valor padrão da imagem
});

const errors = ref({
    title: '',
    authors: '',
    description: '',
    publisher: '',
    language: '',
    categories: '',
    page_count: '',
    published_date: ''
});

const validateForm = () => {
    let isValid = true;
    const lettersOnly = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]+$/;

    if (!book.value.title) {
        errors.value.title = 'Título é obrigatório';
        isValid = false;
    } else {
        errors.value.title = '';
    }

    if (book.value.authors.length === 0) {
        errors.value.authors = 'Pelo menos um autor é obrigatório';
        isValid = false;
    } else if (!book.value.authors.every(author => lettersOnly.test(author))) {
        errors.value.authors = 'Autor deve conter apenas letras';
        isValid = false;
    } else {
        errors.value.authors = '';
    }

    if (!book.value.description) {
        errors.value.description = 'Descrição é obrigatória';
        isValid = false;
    } else {
        errors.value.description = '';
    }

    if (book.value.publisher && !lettersOnly.test(book.value.publisher)) {
        errors.value.publisher = 'Editora deve conter apenas letras';
        isValid = false;
    } else {
        errors.value.publisher = '';
    }

    if (book.value.language && !lettersOnly.test(book.value.language)) {
        errors.value.language = 'Idioma deve conter apenas letras';
        isValid = false;
    } else {
        errors.value.language = '';
    }

    if (book.value.categories.length > 0 && !book.value.categories.every(category => lettersOnly.test(category))) {
        errors.value.categories = 'Categoria deve conter apenas letras';
        isValid = false;
    } else {
        errors.value.categories = '';
    }

    if (book.value.page_count !== null && book.value.page_count <= 0) {
        errors.value.page_count = 'Número de páginas deve ser maior que zero';
        isValid = false;
    } else {
        errors.value.page_count = '';
    }

    if (book.value.published_date !== null && (book.value.published_date < 1000 || book.value.published_date > new Date().getFullYear())) {
        errors.value.published_date = 'Ano de publicação inválido';
        isValid = false;
    } else {
        errors.value.published_date = '';
    }

    return isValid;
};

const addBook = async () => {
    if (!validateForm()) return;

    try {
        const id = await bookService.insert(book.value);
        alert('Livro Cadastrado');
        console.log(book.value);
        console.log(id);
        router.back();
    } catch (error) {
        alert('Erro ao cadastrar livro: ' + error.message);
    }
};

const clickUploadButton = () => {
    addBook();
};

const changeImage = () => {
    const url = prompt('Digite a URL da imagem da capa:');
    if (url && url.startsWith('http')) {
        book.value.image_link = url;
    } else {
        book.value.image_link = imagePreview;
    }
};
</script>

<template>
    <BaseLayout>
        <section class="container">
            <h2 class="mb-4"><i class="fa-solid fa-paperclip me-3"></i>Cadastrar livros</h2>
            <div class="upload-div">
                <img :src="book.image_link" class="imgdefault" alt="Capa do Livro" @click="changeImage">
            </div>

            <div class="form-div">
                <form class="mt-5">
                    <div class="form-group">
                        <input type="text" v-model="book.title" class="form-control" placeholder="Título">
                        <span class="text-danger">{{ errors.title }}</span>
                    </div>
                    <div class="form-group">
                        <input
                            type="text"
                            placeholder="Adicionar Autor (separe com vírgulas)"
                            class="form-control"
                            @blur="book.authors = $event.target.value.split(',').map(a => a.trim())"
                        />
                        <span class="text-danger">{{ errors.authors }}</span>
                    </div>
                    <div class="form-group">
                        <input
                            type="text"
                            placeholder="Adicionar Categoria (separe com vírgulas)"
                            class="form-control"
                            @blur="book.categories = $event.target.value.split(',').map(c => c.trim())"
                        />
                    </div>
                    <div class="form-group">
                        <textarea class="form-control" v-model="book.description" placeholder="Descrição" rows="4"></textarea>
                        <span class="text-danger">{{ errors.description }}</span>
                    </div>
                    <div class="form-group">
                        <input type="text" v-model="book.publisher" class="form-control" placeholder="Editora">
                    </div>
                    <div class="form-group d-flex">
                        <input type="number" v-model="book.page_count" class="form-control me-5" placeholder="Número de Páginas">
                        <span class="text-danger">{{ errors.page_count }}</span>
                        <input type="number" v-model="book.published_date" class="form-control" placeholder="Ano de Publicação">
                        <span class="text-danger">{{ errors.published_date }}</span>
                    </div>
                    <div class="form-group">
                        <input type="text" v-model="book.language" class="form-control" placeholder="Idioma">
                    </div>
                    <button @click="clickUploadButton()" type="button" class="btn btn-primary btn-lg custom-btn">Cadastrar</button>
                </form>
            </div>
        </section>
    </BaseLayout>
</template>


<style scoped>
section {
    margin: 30px;
    width: 80%;
    text-align: center;
    margin: auto;

    .imgdefault {
        height: 350px;
        width: 250px;
        transition: all 0.3s ease;

        &:hover {
            transform: scale(1.1);
        }
    }

    .input-upload {
        display: none;
    }

    .form-group {
        margin: 30px;
    }

    .custom-btn {
        margin: 30px;
        background-color: var(--color_1);
        transition: all 0.3s ease;

        &:hover {
            background-color: var(--color_1_hover);
            transform: scale(1.1);
        }
    }
}
</style>