<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import livroService from '@/services/LivroService';
import { modelLivro } from '@/models/modelLivro';

import BaseLayout from '@/components/BaseLayout.vue';
import EditBook from '@/components/EditBook.vue';

const livro = ref(modelLivro());

const route = useRoute();
const defaultImage = '/img/bookImg.png';

const fetchLivroData = async (livroId) => {
  try {
    const response = await livroService.findById(livroId);
    livro.value = response.data;
  } catch (error) {
    console.error('Erro ao carregar os dados do livro:', error);
  }
};

onMounted(async () => {
  await fetchLivroData(route.params.id);
});
</script>

<template>
  <BaseLayout>
    <section class="row book-detail">
      <aside class="col-12 col-sm-4 d-flex">
        <img :src="livro.capa" alt="Capa do livro" class="book-image" />
        <EditBook :livro="livro" />
      </aside>

      <div class="col-12 col-sm-8">
        <h2>{{ livro.titulo || 'Título não disponível' }}</h2>
        <p><strong>Autor(es):</strong> {{ livro.idAutor?.join(', ') || 'Não informado' }}</p>
        <p><strong>Categoria(s):</strong> {{ livro.idCategorias?.join(', ') || 'Não informado' }}</p>
        <p><strong>Descrição:</strong> {{ livro.descricao || 'Descrição indisponível' }}</p>
        <p><strong>Páginas:</strong> {{ livro.pagina_count || 'Não informado' }}</p>
        <p><strong>Data de Publicação:</strong> {{ livro.anoPublicacao || 'Não informado' }}</p>
        <p><strong>Editora:</strong> {{ livro.editora || 'Não informado' }}</p>
      </div>
    </section>
  </BaseLayout>
</template>

<style scoped>
.book-detail {
  margin: 50px;
}

.custom-btn {
  margin: 10px;
  background-color: var(--color_1);
  color: var(--color_white);
}

.book-image {
  height: 500px;
  width: 350px;
  border-radius: 8px;
  border: 2px solid red;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
</style>