import {Container,Box,TextInput,Group,Button,Card,Center,Title} from "@mantine/core"
import {useForm} from "@mantine/form"

const Register = () => {


    
    const form = useForm({
        initialValues : {
            firstName: "",
            lastName: "",
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
      mt="lg"
        required
        label="First Name"
        placeholder="Jane"
        {...form.getInputProps('firstName')}
      />
      <TextInput
        required
        mt="lg"
        label="Last Name"
        placeholder="Doe"
        {...form.getInputProps('lastName')}
      />
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

export default Register