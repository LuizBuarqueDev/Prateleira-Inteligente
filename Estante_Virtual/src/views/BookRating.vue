
<script setup>
import { ref } from 'vue';
import PrateleiraService from '@/services/PrateleiraService';

const props = defineProps({
  livroId: {
    type: String,
    required: true
  },
  userId: {
    type: String,
    default: '1'
  },
  initialRating: {
    type: Number,
    default: 0
  }
});

const rating = ref(props.initialRating);
const hoverRating = ref(0);

const rate = async (value) => {
  try {
    await PrateleiraService.avaliarLivro(props.userId, props.livroId, value);
    rating.value = value;
  } catch (error) {
    console.error('Erro ao avaliar livro:', error);
  }
};
</script>

<template>
  <div class="rating">
    <span v-for="star in 5" :key="star"
          @click="rate(star)"
          @mouseover="hoverRating = star"
          @mouseleave="hoverRating = 0"
          :class="{ 'filled': star <= (hoverRating || rating) }">
      ★
    </span>
  </div>
</template>

<style scoped>
.rating {
  font-size: 1.5em;
  color: #ccc;
}

.rating span {
  cursor: pointer;
}

.rating .filled {
  color: gold;
}
</style>