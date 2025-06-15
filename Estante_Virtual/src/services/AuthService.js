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
      const payloadBase64 = token.split('.')[1];
      const decodedPayload = atob(payloadBase64);
      return JSON.parse(decodedPayload);
    } catch (e) {
      console.error('Erro ao decodificar token JWT:', e);
      return null;
    }
  }

  async login(nomeUsuario, senha) {
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
  }

  async createUser(nomeUsuario, senha) {
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
  }

  logout() {
    localStorage.removeItem('token');
    this.token.value = '';
    this.isAuthenticated.value = false;
    this.currentUser.value = null;
    router.push('/');
  }

  getUserName() {
    return this.currentUser.value?.sub || '';
  }

  getUserRole() {
    return this.currentUser.value?.role || 'USER';
  }

  getUserId() {
    return this.currentUser.value?.id || null;
  }
}

export default new AuthService();