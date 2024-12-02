<script setup>
import { ref } from 'vue';
import { auth, createUserWithEmailAndPassword } from '@/assets/js/firebase';

const newUser = ref({
  username: '',
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
    // Cria o usuário no Firebase Authentication
    const userCredential = await createUserWithEmailAndPassword(
      auth,
      newUser.value.email,
      newUser.value.password
    );
    const user = userCredential.user;
    console.log('Usuário registrado:', user);

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
      <input type="text" v-model="newUser.username" class="form-control form-control-lg" />
      <label class="form-label">Seu nome de usuario</label>
    </div>

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