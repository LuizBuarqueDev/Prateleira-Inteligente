<script setup>
import { ref, onMounted, watch, computed } from 'vue';

import AuthService from '@/services/AuthService';
import DAOService from '@/services/DAOService';

const currentUser = computed(() => AuthService.currentUser.value);
const isAuthenticated = computed(() => AuthService.isAuthenticated.value);

const userComment = ref({
  userName: '',
  userUID: '',
  message: '',
  date: '',
  idBook: ''
});

const commentsList = ref([]);

const commentsService = new DAOService('comments');

const props = defineProps({
  bookId: {
    type: String,
    required: true
  }
});

const setUserComment = () => {
  if (currentUser.value) {
    userComment.value.userName = currentUser.value.email;
    userComment.value.userUID = currentUser.value.uid;
  }
};

const postComment = async () => {
  if (!userComment.value.message.trim()) return;

  if (!userComment.value.message.trim()) {
    alert("O campo de mensagem não pode estar vazio.");
    return;
  }

  if (userComment.value.message.length > 700) {
    alert("O comentário deve ter no máximo 700 caracteres.");
    return;
  }

  userComment.value.date = new Date().toLocaleString('pt-BR');
  userComment.value.idBook = props.bookId;

  await commentsService.insert(userComment.value);
  console.log(userComment.value);

  userComment.value.message = '';
  fetchCommentsList();
  alert("Comentário publicado com sucesso!");
};

const fetchCommentsList = async () => {
  commentsList.value = await commentsService.search('idBook', props.bookId);

    // Ordenando os comentários do mais recente para o mais antigo
    commentsList.value = comments.sort((a, b) => {
    return new Date(b.date) - new Date(a.date); // Comparação de datas
  });
};

onMounted(() => {
  fetchCommentsList();
  setUserComment();
});

// Atualiza os dados quando ele muda
watch(() => props.bookId, async () => {
  await fetchCommentsList();
});

// Atualiza os dados quando ele muda
watch(currentUser, () => {
  setUserComment();
});
</script>



<template>
  <section class="container">
    <h3><i class="fa-regular fa-comments me-3"></i>Comentários</h3>
    <hr>

    <div v-show="isAuthenticated" class="comment-input">
      <textarea class="form-control input-text" v-model="userComment.message" rows="5"
        placeholder="Deixe seu comentário..."></textarea>
        <span class="text-danger" v-if="userComment.message.length > 700">
        O comentário deve ter no máximo 700 caracteres.
      </span>

      <div class="d-flex justify-content-end mt-2">
        <button @click="postComment" type="button" class="btn btn-secondary m-1">Comentar</button>
      </div>
    </div>

    <div v-for="comment in commentsList" :key="comment.id" class="comment-item">
      <div class="d-flex justify-content-between">
        <span><strong>{{ comment.userName }}</strong></span>
        <span class="text-muted">{{ comment.date }}</span>
      </div>
      <div class="comment-body">
        <p>{{ comment.message }}</p>
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