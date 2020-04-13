<template>
  <v-container>
    <v-row>
      <v-col cols="12">
      <v-btn
        dark
        fab
        top
        right
        color="primary"
        @click="showDialog = true"
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>
        <v-list>
          <v-list-item v-for="location in locations" :key="location.id">
            {{location.id}}: {{location.title}}
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
  <v-dialog v-model="showDialog">
    <v-card>
      <v-card-title>Create location</v-card-title>
      <v-card-text>
        <v-container>
          <v-text-field label="Title*" required v-model="newLocation.title"></v-text-field>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-btn @click="createLocation">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'
import { LocationsApi, Location } from '@/api/generated'

const api = new LocationsApi()

export default Vue.extend({
  name: 'LocationList',

  data: () => ({
    locations: new Array<Location>(),
    showDialog: false,
    newLocation: {
      title: ''
    }
  }),
  methods: {
    fetchLocations () {
      api.getLocations()
        .then(locations => {
          this.locations = locations
        })
    },
    createLocation () {
      api
        .createLocation({ location: this.newLocation })
        .then(this.fetchLocations)
    }
  },
  created () {
    this.fetchLocations()
  }
})
</script>
