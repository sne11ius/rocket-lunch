<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-list>
          <v-list-item v-for="location in locations" :key="location.id">
            {{location.id}}: {{location.title}}
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'
import { LocationsApi, Location } from '@/api/generated'

export default Vue.extend({
  name: 'HelloWorld',

  data: () => ({
    locations: new Array<Location>()
  }),
  created () {
    const api = new LocationsApi()
    api.getLocations()
      .then(locations => {
        this.locations = locations
      })
  }
})
</script>
