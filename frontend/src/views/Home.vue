<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <LocationList />
  </div>
</template>

<script>
// @ is an alias to /src
import LocationList from '@/components/LocationList.vue'

export default {
  name: 'Home',
  components: {
    LocationList
  }
}
</script>
