<script setup>
import { ref } from 'vue';
import BaseLayout from '@/components/BaseLayout.vue';

import DAOService from '@/services/DAOService';

const bookService = new DAOService('books');

const fileInput = ref(null);
const imagePreview = ref('/img/bookImg.png');

const book = ref({
    title: '',
    authors: [],
    categories: [],
    description: '',
    publisher: '',
    page_count: null,
    published_date: null,
    language: '',
    image_link: imagePreview.value // Valor padrão da imagem
});

const addBook = async () => {
    const id = await bookService.insert(book.value);
    alert('Livro Cadastrado');
    console.log(book.value);
    console.log(id);
};

const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
            book.value.image_link = e.target.result; // Atualiza a imagem
        };
        reader.readAsDataURL(file);
    }
};

const triggerFileInput = () => {
    fileInput.value.click();
};

const clickUploadButton = () => {
    console.log(book.value);
    addBook();
};
</script>

<template>
    <BaseLayout>
        <section class="container">
            <h2 class="mb-4"><i class="fa-solid fa-paperclip me-3"></i>Cadastrar livros</h2>
            <div class="upload-div">
                <img :src="book.image_link" class="imgdefault" alt="Capa do Livro" @click="triggerFileInput">

                <input class="input-upload" ref="fileInput" type="file" name="capa-upload"
                    accept="image/png, image/jpeg" @change="handleFileChange">
            </div>

            <div class="form-div">
                <form class="mt-5">
                    <div class="form-group">
                        <input type="text" v-model="book.title" class="form-control" placeholder="Título">
                    </div>
                    <div class="form-group">
                        <input
                            type="text"
                            placeholder="Adicionar Autor (separe com vírgulas)"
                            class="form-control"
                            @blur="book.authors = $event.target.value.split(',').map(a => a.trim())"
                        />
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
                        <textarea class="form-control" v-model="book.description" placeholder="Descrição"
                            rows="4"></textarea>
                    </div>
                    <div class="form-group">
                        <input type="text" v-model="book.publisher" class="form-control" placeholder="Editora">
                    </div>
                    <div class="form-group d-flex">
                        <input type="number" v-model="book.page_count" class="form-control me-5"
                            placeholder="Número de Páginas">
                        <input type="number" v-model="book.published_date" class="form-control"
                            placeholder="Ano de Publicação">
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