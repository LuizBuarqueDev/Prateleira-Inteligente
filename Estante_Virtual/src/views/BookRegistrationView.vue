<script setup>
import { ref } from 'vue';
import BaseLayout from '@/components/BaseLayout.vue';
import livroService from '@/services/LivroService';
import router from '@/router';

const imagePreview = '/img/bookImg.png';

const book = ref({
    titulo: '',
    idAutor: null,
    idCategorias: null,
    descricao: '',
    editora: '',
    anoPublicacao: null,
    idUsuarios: [],
    image_link: imagePreview
});

const addBook = async () => {
    try {
        await livroService.create(book.value);
        alert('Livro Cadastrado');
        console.log(book.value);
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
                        <input type="text" v-model="book.titulo" class="form-control" placeholder="Título">
                    </div>

                    <div class="form-group">
                        <input type="number" v-model="book.anoPublicacao" class="form-control" placeholder="Ano de Publicação">
                    </div>

                    <div class="form-group">
                        <textarea class="form-control" v-model="book.descricao" placeholder="Descrição" rows="4"></textarea>
                    </div>

                    <div class="form-group">
                        <input type="text" v-model="book.editora" class="form-control" placeholder="Editora">
                    </div>

                    <div class="form-group">
                        <input type="text" v-model="book.idAutor" class="form-control" placeholder="ID do Autor">
                    </div>

                    <div class="form-group">
                        <input type="text" v-model="book.idCategorias" class="form-control" placeholder="ID da Categoria">
                    </div>

                    <div class="form-group">
                        <input type="text"
                            placeholder="IDs dos Usuários (separe por vírgula)"
                            class="form-control"
                            @blur="book.idUsuarios = $event.target.value.split(',').map(id => id.trim())"
                        />
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