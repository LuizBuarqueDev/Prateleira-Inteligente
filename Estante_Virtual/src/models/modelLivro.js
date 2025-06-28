export const modelLivro = () => ({
  id: null,
  titulo: '',
  capa: '',
  anoPublicacao: '',
  descricao: '',
  editora: '',
  autor: {
    id: null,
    name:null,
    idLivros: null
  },
  idCategorias: [],
  idUsuarios: [],
  idComentarios: [],
  mediaAvaliacoes: 0
});
