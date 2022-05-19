import {Container,Box,TextInput,Group,Button,Card,Center,Title} from "@mantine/core"
import {useForm} from "@mantine/form"

const Login = () => {

    const form = useForm({
        initialValues : {

            email:"",
            password: "",
        }
    })



const onSubmit = (values)=>{
console.log(values);
}


  return (
    <Container>
        
    <Box mt={20} my="xl" sx={{ maxWidth: 300 }} mx="auto">
<Card shadow="md" >
<form onSubmit={form.onSubmit(onSubmit)}>
    <Center><Title color="orange" order={2}>Register</Title></Center>
  
  <TextInput
    required
    mt="lg"
    label="Email"
    placeholder="jane@doe.com"
    {...form.getInputProps('email')}
  />
  <TextInput
    required
    mt="lg"
    label="Password"
    placeholder="*******"
    {...form.getInputProps('password')}
  />

  <Group position="center" mt="md">
    <Button color="orange" radius="md" type="submit">Submit</Button>
  </Group>
</form>
</Card>
</Box>
</Container>
  )
}

export default Login