import api from '@/assets/js/axios';

const COMENTARIO_ENDPOINT = '/comentario';

export default {
  create(comentaio) {
    return api.post(`${COMENTARIO_ENDPOINT}/criar`, comentaio);
  },

  findById(id) {
    return api.get(`${COMENTARIO_ENDPOINT}/${id}`);
  },

  delete(id) {
    return api.delete(`${COMENTARIO_ENDPOINT}/deletar/${id}`);
  },

  findAllByIds(ids) {
    const params = new URLSearchParams();
    ids.forEach(id => params.append('ids', id));
    return api.get(`${COMENTARIO_ENDPOINT}/encontrarPorID?${params.toString()}`);
  },

  findAll() {
    return api.get(COMENTARIO_ENDPOINT);
  }
};