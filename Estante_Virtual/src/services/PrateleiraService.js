import api from '@/assets/js/axios';

const PRATELEIRA_ENDPOINT = '/prateleira';

export default {
    adicionarLivro(prateleiraDTO) {
        return api.post(PRATELEIRA_ENDPOINT, prateleiraDTO);
    },

    removerLivro(idUsuario, idLivro) {
        return api.delete(`${PRATELEIRA_ENDPOINT}/${idUsuario}/${idLivro}`);
    },

    listarLivrosPorUsuario(idUsuario) {
        return api.get(`${PRATELEIRA_ENDPOINT}/usuario/${idUsuario}`);
    },

    buscarLivroNaPrateleira(idUsuario, idLivro) {
        return api.get(`${PRATELEIRA_ENDPOINT}/${idUsuario}/${idLivro}`);
    }
};