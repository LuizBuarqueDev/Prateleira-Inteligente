<script setup>
<<<<<<< HEAD
import { ref } from 'vue';
import { auth, signInWithEmailAndPassword } from '@/assets/js/firebase';

=======

import { ref } from 'vue';
import { auth } from '@/assets/js/firebase';
import { signInWithEmailAndPassword } from 'firebase/auth';
>>>>>>> 42eeb8ff975fc61912ffd25e58d3c46ec48c3e69


const loginData = ref({
  email: '',
  password: ''
});

const loginUser = async () => {
  try {const userCredential = await signInWithEmailAndPassword(
      auth,
      loginData.value.email,
      loginData.value.password
    );

    const user = userCredential.user;
    console.log('Usuário logado:', user);
<<<<<<< HEAD

    alert('Login realizado com sucesso!');
=======
    alert('Login realizado com sucesso!');

    loginData.value.email = ''
    loginData.value.password = ''
>>>>>>> 42eeb8ff975fc61912ffd25e58d3c46ec48c3e69
  } catch (error) {
    console.error('Erro ao fazer login:', error.message);
    alert('Email ou senha incorretos!');
  }
};

</script>
<<<<<<< HEAD
<form @submit.prevent="loginUser"></form>
=======

>>>>>>> 42eeb8ff975fc61912ffd25e58d3c46ec48c3e69
<template>
  
  <form>
    <div>
      <i class="fa-solid fa-book-bookmark"></i>
      <span class="h1 fw-bold">Prateleira Inteligente</span>
    </div>

    <h5>Entre na sua conta</h5>
    <div>
<<<<<<< HEAD
      <input type="email" v-model="loginData.email" class="form-control form-control-lg" />
=======
      <input type="email" v-model="loginData.email" class="form-control form-control-lg"/>
>>>>>>> 42eeb8ff975fc61912ffd25e58d3c46ec48c3e69
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
      color:var(--color_4);
    }
  }
}
</style>