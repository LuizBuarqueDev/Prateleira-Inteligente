import api from '@/assets/js/axios';

const LIVRO_ENDPOINT = '/livro';

export default {
  create(livro) {
    return api.post(`${LIVRO_ENDPOINT}/create`, livro);
  },

  findById(id) {
    return api.get(`${LIVRO_ENDPOINT}/${id}`);
  },

  update(id, livro) {
    return api.put(`${LIVRO_ENDPOINT}/update/${id}`, livro);
  },

  delete(id) {
    return api.delete(`${LIVRO_ENDPOINT}/delete/${id}`);
  },

  findAllByIds(ids) {
    const params = new URLSearchParams();
    ids.forEach(id => params.append('ids', id));
    return api.get(`${LIVRO_ENDPOINT}/findByIds?${params.toString()}`);
  },

  findAll() {
    return api.get(LIVRO_ENDPOINT);
  },

  search(termo) {
        return api.get(`${LIVRO_ENDPOINT}/buscar`, {
      params: { busca: termo }
    });
  },

  findByCategoriaId(categoriaId) {
    return api.get(`${LIVRO_ENDPOINT}/categoria/${categoriaId}`);
  }
};