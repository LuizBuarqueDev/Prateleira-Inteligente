<script setup>
import { ref, watch, nextTick } from 'vue';
import { modelLivro } from '@/models/modelLivro';
import { format } from 'date-fns';

import router from '@/router';

import BaseLayout from '@/components/BaseLayout.vue';
import AutorCard from '@/components/AutorCard.vue';

import livroService from '@/services/LivroService';

import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'


const date = ref(null);
const livro = ref(modelLivro());

watch(date, (newDate) => {
    if (newDate) {
        livro.value.anoPublicacao = format(newDate, 'yyyy-MM-dd'); // armazenar em formato ISO
    }
});

const addLivro = async () => {
    try {
        await livroService.create(livro.value);
        alert('Livro Cadastrado');
        console.log(livro.value);
        await nextTick(); // espera DOM estabilizar
        router.back();
    } catch (error) {
        alert('Erro ao cadastrar livro: ' + error.message);
    }
};

const handleAutorSelecionado = (id) => {
  livro.value.autor.id = id;
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
                <img :src="livro.capa" @error="livro.capa = '/img/bookImg.png'" class="imgdefault" alt="Capa do Livro" @click="changeImage">
            </div>

            <div class="form-div">
                <form @submit.prevent class="mt-5">
                    <div class="form-group">
                        <input type="text" v-model="livro.titulo" class="form-control" placeholder="Título">
                    </div>

                    <div class="form-group">
                        <VueDatePicker v-model="date" placeholder="Selecione a data de publicação"
                            :format="(d) => d ? d.toLocaleDateString('pt-BR') : ''" />
                    </div>

                    <div class="form-group">
                        <textarea class="form-control" v-model="livro.descricao" placeholder="Descrição"
                            rows="4"></textarea>
                    </div>

                    <div class="form-group">
                        <input type="text" v-model="livro.editora" class="form-control" placeholder="Editora">
                    </div>

                    <div class="form-group">
                    <AutorCard @update:selectedAuthorId="handleAutorSelecionado"/>
                    </div>

                    <div class="form-group">
                        <input type="text" v-model="livro.idCategorias" class="form-control"
                            placeholder="ID da Categoria">
                    </div>

                    <div class="form-group">
                        <input type="text" placeholder="IDs dos Usuários (separe por vírgula)" class="form-control"
                            @blur="livro.idUsuarios = $event.target.value.split(',').map(id => id.trim())" />
                    </div>

                    <button @click="clickUploadButton()" type="button"
                        class="btn btn-primary btn-lg custom-btn">Cadastrar</button>
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