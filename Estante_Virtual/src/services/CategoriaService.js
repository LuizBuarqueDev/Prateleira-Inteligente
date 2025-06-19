import api from '@/assets/js/axios';

const CATEGORIA_ENDPOINT = '/api/categoria';

export default {
  findAll() {
    return api.get(CATEGORIA_ENDPOINT);
  },
  
  findById(id) {
    return api.get(`${CATEGORIA_ENDPOINT}/${id}`);
  }
};