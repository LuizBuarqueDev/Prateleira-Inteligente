<script setup>
import { ref } from 'vue';

import AuthService from '@/services/AuthService';


const loginData = ref({
  email: '',
  password: ''
});

const loginUser = async () => {
  AuthService.login(loginData.value.email, loginData.value.password);
};

</script>



<template>
  
  <form>
    <div>
      <i class="fa-solid fa-book-bookmark"></i>
      <span class="h1 fw-bold">Prateleira Inteligente</span>
    </div>

    <h5>Entre na sua conta</h5>
    <div>
      <input type="email" v-model="loginData.email" class="form-control form-control-lg"/>
      <label class="form-label" for="loginEmail">Endereço de email</label>
    </div>

    <div>
      <input type="password" v-model="loginData.password" class="form-control form-control-lg" />
      <label class="form-label" for="loginPassword">Senha</label>
    </div>

    <div>
      <button @click="loginUser" class="btn btn-dark btn-lg btn-block" type="button">Login</button>
    </div>

    <a href="#!">Esqueceu sua senha ?</a>
    <p>Não tem uma conta?
      <a href="#" @click="$emit('switchToRegister')">Registre-se aqui</a>
    </p>
  </form>
</template>



<style scoped>
form {

  div {
    margin-bottom: 16px;
    padding-bottom: 4px;

    input {
      border-color: gray;
    }
  }

  i {
    font-size: 2em;
    margin-right: 10px
  }

  a {
    font-size: small;
    color: var(--color_black);
  }

  p {
    margin-top: 16px;
    color: var(--color_4);

    a {
      color: var(--color_4);
    }
  }
}
</style>