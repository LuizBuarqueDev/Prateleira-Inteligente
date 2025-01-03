<script setup>
import { ref } from 'vue';

import AuthService from '@/services/AuthService';


const newUser = ref({
  email: '',
  password: '',
  repeatPassword: ''
});

const registerUser = async () => {
  if (newUser.value.password !== newUser.value.repeatPassword) {
    alert('As senhas não coincidem!');
    return;
  }

  try {
    AuthService.createUser(newUser.value.email,newUser.value.password);
    alert('Conta criada com sucesso!');
    
  } catch (error) {
    console.error('Erro ao registrar:', error.message);
    alert(error.message);
  }
};
</script>



<template>

  <form>
    <div class="mb-3 pb-1">
      <i class="fa-solid fa-book-bookmark"></i>
      <span class="h1 fw-bold">Prateleira Inteligente</span>
    </div>

    <h5 class="mb-3 pb-3">Crie sua conta</h5>

    <div>
      <input type="email" v-model="newUser.email" class="form-control form-control-lg" />
      <label class="form-label">Endereço de email</label>
    </div>

    <div>
      <input type="password" v-model="newUser.password" class="form-control form-control-lg" />
      <label class="form-label">Senha</label>
    </div>

    <div>
      <input type="password" v-model="newUser.repeatPassword" class="form-control form-control-lg" />
      <label class="form-label">Repita a senha</label>
    </div>

    <div>
      <button @click="registerUser" class="btn btn-dark btn-lg btn-block" type="button">Criar</button>
    </div>

    <p>
      Já tem uma conta?
      <a href="#" @click="$emit('switchToLogin')">Login here</a>
    </p>
  </form>

</template>



<style scoped>
form {

  div {
    margin-bottom: 16px;

    input {
      border-color: gray;
    }
  }

  p,
  a {
    color: var(--color_4);
  }

  i {
    font-size: 2em;
    margin-right: 10px
  }
}
</style>