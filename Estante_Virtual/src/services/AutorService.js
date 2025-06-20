import api from '@/assets/js/axios';

const LIVRO_ENDPOINT = '/autor';

export default {
  create(livro) {
    return api.post(`${LIVRO_ENDPOINT}/criar`, livro);
  },

  findById(id) {
    return api.get(`${LIVRO_ENDPOINT}/${id}`);
  },

  update(id, livro) {
    return api.put(`${LIVRO_ENDPOINT}/atualizar/${id}`, livro);
  },

  delete(id) {
    return api.delete(`${LIVRO_ENDPOINT}/deletar/${id}`);
  },

  findAllByIds(ids) {
    const params = new URLSearchParams();
    ids.forEach(id => params.append('ids', id));
    return api.get(`${LIVRO_ENDPOINT}/encontrarPorID?${params.toString()}`);
  },

  findAll() {
    return api.get(LIVRO_ENDPOINT);
  }
};