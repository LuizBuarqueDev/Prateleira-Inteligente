import router from '@/router';
import { ref } from 'vue';
import api from '@/assets/js/axios';

class AuthService {
  constructor() {
    this.isAuthenticated = ref(false);
    this.currentUser = ref(null);
    this.token = ref(localStorage.getItem('token') || '');

    if (this.token.value) {
      this.isAuthenticated.value = true;
      this.currentUser.value = this._parseToken(this.token.value);
    }
  }

  _parseToken(token) {
    try {
      const payload = token.split('.')[1];
      return JSON.parse(atob(payload));
    } catch (e) {
      return null;
    }
  }

  async login(nomeUsuario, senha) {
    try {
      const response = await api.post('/auth/login', {
        nomeUsuario,
        senha
      });

      const token = response.data.token;
      localStorage.setItem('token', token);
      this.token.value = token;
      this.isAuthenticated.value = true;
      this.currentUser.value = this._parseToken(token);
      router.push('/');
    } catch (error) {
      alert('Erro ao realizar login: ' + error.response?.data?.message || error.message);
      throw error;
    }
  }

  async createUser(nomeUsuario, senha) {
    try {
      const response = await api.post('/auth/registrar', {
        nomeUsuario,
        senha
      });

      const token = response.data.token;
      localStorage.setItem('token', token);
      this.token.value = token;
      this.isAuthenticated.value = true;
      this.currentUser.value = this._parseToken(token);
      router.push('/');
    } catch (error) {
      alert('Erro ao criar usuário: ' + error.response?.data?.message || error.message);
      throw error;
    }
  }

  logout() {
    localStorage.removeItem('token');
    this.token.value = '';
    this.isAuthenticated.value = false;
    this.currentUser.value = null;
    router.push('/');
  }
}

export default new AuthService();
