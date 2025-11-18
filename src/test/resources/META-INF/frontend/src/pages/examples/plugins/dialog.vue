<template>

<table>
<tbody>
    <tr>
        <th><v-btn @click="dialog1">Dialog1</v-btn></th>
    </tr>
</tbody>

</table>

</template>


<script >
import dialog from '@/plugins/dialog';


export default {

  data: () => ({
    timestamp : undefined
  }),

  methods : {

    dialog1(){
        this.$dialog.confirm("1")
            .then((r)=>{
                console.log("then", r);
            })
            .catch((r)=>{
                console.log("catch", r);
            });
    },
    dialog2(){
        
    },
    dialog3(){
        
    },
    dialog4(){
        
    }
  },

  mounted(){
    this.timestamp = this.$moment.parse();
  }
}


  //
</script>