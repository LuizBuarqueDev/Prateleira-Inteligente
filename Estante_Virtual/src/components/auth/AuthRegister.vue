<script setup>
import { ref } from 'vue';

import AuthService from '@/services/AuthService';


const newUser = ref({
  email: '',
  password: '',
  repeatPassword: ''
});

const errors = ref({
  email: '',
  password: '',
  repeatPassword: ''
});

const validateEmail = (email) => {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
};

const validatePassword = (password) => {
  return password.length >= 6;
};

const validateForm = () => {
  let isValid = true;

  if (!newUser.value.email) {
    errors.value.email = ' | Email é obrigatório';
    isValid = false;
  } else if (!validateEmail(newUser.value.email)) {
    errors.value.email = ' | Email inválido';
    isValid = false;
  } else {
    errors.value.email = '';
  }

  if (!newUser.value.password) {
    errors.value.password = ' | Senha é obrigatória';
    isValid = false;
  } else if (!validatePassword(newUser.value.password)) {
    errors.value.password = ' | A senha deve ter pelo menos 6 caracteres';
    isValid = false;
  } else {
    errors.value.password = '';
  }

  if (newUser.value.password !== newUser.value.repeatPassword) {
    errors.value.repeatPassword = '  | As senhas não coincidem';
    isValid = false;
  } else {
    errors.value.repeatPassword = '';
  }

  return isValid;
};

const registerUser = async () => {
  if (!validateForm()) return;
    await AuthService.createUser(newUser.value.email, newUser.value.password);
    alert('Conta criada com sucesso!');
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
      <span class="text-danger">{{ errors.email }}</span>
    </div>

    <div>
      <input type="password" v-model="newUser.password" class="form-control form-control-lg" />
      <label class="form-label">Senha</label>
      <span class="text-danger">{{ errors.password }}</span>
    </div>

    <div>
      <input type="password" v-model="newUser.repeatPassword" class="form-control form-control-lg" />
      <label class="form-label">Repita a senha</label>
      <span class="text-danger">{{ errors.repeatPassword }}</span>
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