<script setup>
import { ref, onMounted } from 'vue';
import { auth, onAuthStateChanged } from '@/assets/js/firebase';

import UserTag from '../UserTag.vue';

const isAuthenticated = ref(false);

// Verifica se o usuário está autenticado
onMounted(() => {
  onAuthStateChanged(auth, (user) => {
    isAuthenticated.value = !!user;
  });
});
</script>

<template>
  <nav class="navbar navbar-expand-lg custom-nav">
    <div class="container-fluid">
      <router-link class="nav-link navbar-brand" to="/">
        <img src="/img/logoTransparente.png" alt="Logo">
        <span>Prateleira Inteligente</span>
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
              aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/">
              <i class="fa-solid fa-house-chimney"></i>Início
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/">
              <i class="fa-solid fa-magnifying-glass"></i>Buscar
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/bookcase">
              <i class="fa-regular fa-bookmark"></i>Minha estante
            </router-link>
          </li>
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link class="nav-link" to="/authentication">
              <i class="fa-solid fa-right-to-bracket"></i>Entre
            </router-link>
          </li>
          <li class="nav-item" v-else>
            <UserTag />
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>
.custom-nav {
  background-color: var(--color_1);
  font-weight: bold;
  padding: 10px;
  margin-bottom: 20px;

  img {
    height: 40px;
    width: auto;
    margin-right: 20px;
  }

  i {
    margin-right: 10px;
    font-size: 1.5em;
  }

  a {
    color: var(--color_white);
    margin-right: 50px;
    padding: 10px;
    border-radius: 8px;
    transition: all 0.3s ease;

    &:hover {
      background-color: var(--color_1_hover);
      transform: scale(1.1);
    }
  }
}
</style>