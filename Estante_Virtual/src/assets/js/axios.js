import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  }
});

// Interceptor de requisição: adiciona o token
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor de resposta: captura erros globais
instance.interceptors.response.use(
  (response) => response,
  (error) => {
    const data = error.response?.data;
    let message = 'Erro inesperado. Tente novamente mais tarde.';

    // Captura os diferentes possíveis campos de mensagem
    if (typeof data === 'string') {
      message = data;
    } else if (data?.message) {
      message = data.message;
    } else if (data?.erro) {
      message = data.erro;
    } else if (typeof data === 'object') {
      message = JSON.stringify(data, null, 2);
    }

    // Exibição de mensagens conforme status
    if (error.response?.status === 401) {
      alert('Não autorizado: ' + message);
    } else if (error.response?.status === 403) {
      alert('Acesso negado.');
    } else if (error.response?.status === 500) {
      alert('Erro interno do servidor.');
    } else {
      alert('Erro: ' + message);
    }

    return Promise.reject(error);
  }
);

export default instance;