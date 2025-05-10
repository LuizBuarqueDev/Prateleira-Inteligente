<script>
</script>

<template>
    <div class="edit-bar">
        <i class="fa-solid fa-pen-to-square"></i>
        <i class="fa-solid fa-trash"></i>
    </div>
</template>

<style scoped>
.edit-bar {
  display: flex;
  flex-direction: column;
  i {
    padding: 10px;

    &:hover {
      transform: scale(1.3);
      color: #bb0000;
    }
  }
}

</style>