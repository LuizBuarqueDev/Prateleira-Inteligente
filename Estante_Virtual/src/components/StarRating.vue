<script setup>
import { ref, watch } from 'vue';
import PrateleiraService from '@/services/PrateleiraService';

const props = defineProps({
  livroId: String,
  userId: String,
  initialRating: Number
});

const rating = ref(props.initialRating || 0);
const hoverRating = ref(0);

watch(() => props.initialRating, (newVal) => {
  rating.value = newVal || 0;
});

const rate = async (value) => {
  try {
    await PrateleiraService.avaliarLivro(props.userId, props.livroId, value);
    rating.value = value;
  } catch (error) {
    console.error('Erro ao avaliar livro:', error);
  }
};
</script>