<script setup>
import { ref } from 'vue';
import BaseLayout from '@/components/BaseLayout.vue';
import livroService from '@/services/LivroService';
import router from '@/router';
import { modelLivro } from '@/models/modelLivro';

const livro = ref(modelLivro());

const addLivro = async () => {
    try {
        await livroService.create(livro.value);
        alert('Livro Cadastrado');
        console.log(livro.value);
        router.back();
    } catch (error) {
        alert('Erro ao cadastrar livro: ' + error.message);
    }
};

const clickUploadButton = () => {
    addLivro();
};

const changeImage = () => {
    const url = prompt('Digite a URL da imagem da capa:');
    if (url && url.startsWith('http')) {
        livro.value.capa = url;
    } else {
        livro.value.capa = '/img/bookImg.png';
    }
};
</script>

<template>
    <BaseLayout>
        <section class="container">
            <h2 class="mb-4"><i class="fa-solid fa-paperclip me-3"></i>Cadastrar livros</h2>
            <div class="upload-div">
                <img :src="livro.capa" class="imgdefault" alt="Capa do Livro" @click="changeImage">
            </div>

            <div class="form-div">
                <form class="mt-5">
                    <div class="form-group">
                        <input type="text" v-model="livro.titulo" class="form-control" placeholder="Título">
                    </div>

                    <div class="form-group">
                        <input type="number" v-model="livro.anoPublicacao" class="form-control" placeholder="Ano de Publicação">
                    </div>

                    <div class="form-group">
                        <textarea class="form-control" v-model="livro.descricao" placeholder="Descrição" rows="4"></textarea>
                    </div>

                    <div class="form-group">
                        <input type="text" v-model="livro.editora" class="form-control" placeholder="Editora">
                    </div>

                    <div class="form-group">
                        <input type="text" v-model="livro.idAutor" class="form-control" placeholder="ID do Autor">
                    </div>

                    <div class="form-group">
                        <input type="text" v-model="livro.idCategorias" class="form-control" placeholder="ID da Categoria">
                    </div>

                    <div class="form-group">
                        <input type="text"
                            placeholder="IDs dos Usuários (separe por vírgula)"
                            class="form-control"
                            @blur="livro.idUsuarios = $event.target.value.split(',').map(id => id.trim())"
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