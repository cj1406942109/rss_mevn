<template>
    <div class="home">
        <my-nav></my-nav>
        <div class="container">
            <div class="row">                
                <div class="col-md-12">
                    
                </div>
            </div>
        </div>
        
    </div>
</template>

<script>
    import myNav from '@/components/nav/nav';
    export default {
        components: {
            myNav
        }
    }
</script>

<style lang="stylus" scoped>
    .container
        margin-top 50px        
</style>

