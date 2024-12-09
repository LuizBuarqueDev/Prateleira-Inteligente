<script setup>
import { inject} from 'vue';

const searchValue = inject('searchValue')
</script>

<template>
    <div class="d-flex align-items-center me-4">
        <a class="nav-link" data-bs-toggle="collapse" data-bs-target="#collapseTwo">
            <i class="fa-solid fa-magnifying-glass"></i>Buscar
        </a>

        <div id="collapseTwo" class="collapse me-5">
            <input class="form-control form-control-lg" v-model="searchValue" type="text" placeholder="Digite o título...">
        </div>
    </div>
</template>

<style scoped>
a {
    color: var(--color_white);
    margin-right: 10px;
    padding: 10px;
    border-radius: 8px;
    transition: all 0.3s ease;
    width: 110px;

    &:hover {
        color: var(--color_white);
        background-color: var(--color_1_hover);
        transform: scale(1.1);
    }

    i {
        margin-right: 10px;
        font-size: 1.5em;
    }
}
</style>
