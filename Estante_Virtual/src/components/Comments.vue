<script setup>
import { ref, onMounted, computed } from 'vue';

import AuthService from '@/services/AuthService';
import ComentarioService from '@/services/ComentarioService';

const props = defineProps({
  bookId: {
    type: Number,
    required: true
  }
});

const isAuthenticated = computed(() => AuthService.isAuthenticated.value);

const userComment = ref({
  usuario: {
    id: '',
    nome: ''
  },
  texto: '',
  dataCriacao: '',
  idLivro: 0
});

const commentsList = ref([]);

const setUserComment = () => {
  userComment.value.usuario.id = AuthService.getUserId();
  userComment.value.usuario.nome = AuthService.getUserName();
};

const postComment = async () => {
  const texto = userComment.value.texto.trim();

  if (!texto) {
    alert("O campo de mensagem não pode estar vazio.");
    return;
  }

  if (texto.length > 700) {
    alert("O comentário deve ter no máximo 700 caracteres.");
    return;
  }

  userComment.value.dataCriacao = new Date().toISOString();
  userComment.value.idLivro = props.bookId;

  await ComentarioService.create(userComment.value);
  userComment.value.texto = '';

  await fetchCommentsList();
  alert("Comentário publicado com sucesso!");
};

const fetchCommentsList = async () => {
  const response = await ComentarioService.findAllByLivroId(props.bookId);
  commentsList.value = response.data;
};

onMounted(() => {
  fetchCommentsList();
  setUserComment();
});
</script>


<template>
  <section class="container">
    <h3><i class="fa-regular fa-comments me-3"></i>Comentários</h3>
    <hr />

    <div v-show="isAuthenticated" class="comment-input">
      <textarea
        class="form-control input-text"
        v-model="userComment.texto"
        rows="5"
        placeholder="Deixe seu comentário..."
      ></textarea>

      <span class="text-danger" v-if="userComment.texto.length > 700">
        O comentário deve ter no máximo 700 caracteres.
      </span>

      <div class="d-flex justify-content-end mt-2">
        <button @click="postComment" type="button" class="btn btn-secondary m-1">Comentar</button>
      </div>
    </div>

    <div v-for="comment in commentsList" :key="comment.id" class="comment-item">
      <div class="d-flex justify-content-between">
        <span><strong>{{ comment.usuario?.nome || 'Anônimo' }}</strong></span>
        <span class="text-muted">{{ comment.dataCriacao }}</span>
      </div>
      <div class="comment-body">
        <p>{{ comment.texto }}</p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.comment-input {
  border: 2px solid gray;
  border-radius: 8px;

  .input-text {
    border-bottom: 2px solid gray;
  }
}

.comment-item {
  margin-top: 15px;
  border: 2px dotted gray;
  border-radius: 8px;

  div {
    margin: 10px;
  }
}
</style>