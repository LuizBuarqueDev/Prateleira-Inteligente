<script setup>
import AutorService from '@/services/AutorService';
import { onMounted, ref } from 'vue';

const authors_list = ref([]);

const fetchAutores = async () => {
    try {
        const response = await AutorService.findAll();
        authors_list.value = response.data;
        console.log(authors_list.value);
    } catch (error) {
        console.error('Erro ao buscar autores:', error);
    }
};

onMounted(fetchAutores);
</script>


<template>
    <div class="d-flex">
        <select class="form-select">
            <option selected>Selecione o autor</option>
            <option
            v-for="autor in authors_list"
            :key="autor.id"
            :value="autor.id"
            >
            {{ autor.nome }}
            </option>
            
        </select>
        <button class="btn mx-3">
            <i class="fa-solid fa-plus"></i>
        </button>
    </div>
</template>

<style scoped>
button {
    border: 1px solid red;
}

</style>