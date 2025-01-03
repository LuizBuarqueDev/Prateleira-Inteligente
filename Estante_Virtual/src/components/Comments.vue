<script setup>
import { ref, onMounted, watch } from 'vue';
import { auth } from '@/assets/js/firebase';
import { onAuthStateChanged } from 'firebase/auth';

import DAOService from '@/services/DAOService';

const isAuthenticated = ref(false);
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

const setUserComment = (user) => {
  if (user) {
    isAuthenticated.value = true;
    userComment.value.userName = user.email;
    userComment.value.userUID = user.uid
  } else {
    isAuthenticated.value = false;
  }
};

const postComment = async () => {
  if (!userComment.value.message.trim()) return;

  userComment.value.date = new Date().toLocaleString('pt-BR');
  userComment.value.idBook = props.bookId;

  await commentsService.insert(userComment.value);
  console.log(userComment.value)

  userComment.value.message = '';
  fetchCommentsList();
  alert("Comentário publicado com sucesso!");
};

const fetchCommentsList = async () => {
  commentsList.value = await commentsService.search('idBook', props.bookId);
};

onMounted(fetchCommentsList);

onAuthStateChanged(auth, setUserComment);

watch(() => props.bookId, fetchCommentsList);
</script>

<template>
    <section class="container">

        <h3><i class="fa-regular fa-comments me-3"></i>Comentários</h3>
        <hr>

        <div v-show="isAuthenticated" class="comment-input">
            <textarea class="form-control input-text" v-model="userComment.message" rows="5" placeholder="Deixe seu comentário..."></textarea>

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
    margin-top: 30px;
    border: 2px dotted gray;
    border-radius: 8px;
    div {
        margin: 10px;
    }
}
</style>