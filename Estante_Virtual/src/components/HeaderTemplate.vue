<script setup>
import { ref } from 'vue';
import { RouterLink } from 'vue-router';

import AuthService from '@/services/AuthService';


const isAuthenticated = ref(AuthService.isAuthenticated.value);
</script>



<template>
    <header v-show="!isAuthenticated">
        <h1>Gerencie seus livros !</h1>
        <p>
            A Prateleira Inteligente  é o melhor lugar para organizar, gerenciar e compartilhar suas leituras.
            Faça seu login ou cadastre-se gratuitamente para começar agora mesmo a organizar seus livros.
        </p>

        <RouterLink to="/authentication" class="btn btn-primary btn-entrar" role="button">
            Entre!
        </RouterLink>

        <RouterLink to="/authentication" class="btn btn-primary btn-cadastrar" role="button">
            Cadastre-se >>
        </RouterLink>
    </header>
</template>



<style scoped>
header {
    background: var(--color_4);
    padding: 40px;

    h1 {
        color: var(--color_white);
    }

    p {
        color: var(--color_white);
    }

    .btn-primary {
        border-color: var(--color_white);
        margin-left: 20px;
        margin-right: 20px;
    }

    .btn-entrar {
        background-color: var(--color_1);
    }

    .btn-cadastrar {
        background-color: var(--color_2);
    }

    .btn-entrar:hover {
        background-color: var(--color_1_hover);
    }

    .btn-cadastrar:hover {
        background-color: var(--color_2_hover);
    }
}
</style>